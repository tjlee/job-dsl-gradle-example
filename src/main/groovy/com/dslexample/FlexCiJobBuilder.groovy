package com.dslexample

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Class for creating Flex build
 */
class FlexCiJobBuilder {

    String name
    String description
//    String gitHubUrl
    String gitHubCredentials
    String gitHubCheckoutDir
    String gitHubOwnerAndProject
    String antBuildFile
    String antSourceDir
    String antFlexSdkDir
    String antAirSdkDir
    String antBuildTestFile
    String artifacts = '*FLEX.war'
    List<String> emails = []

    Job build(DslFactory dslFactory) {
        dslFactory.job(name) {
            it.description this.description
            logRotator {
                numToKeep 50
            }

            parameters {
                stringParam('VERSION', '10.2.0.0', '')
                booleanParam('FLEX_DEBUG', false, '')
                booleanParam('PROTOCOL_VALIDATION_SKIP', true, '')
                booleanParam('FLEX_TEST_MODE', false, '')
            }

            scm {
                git {
                    remote {
                        github(this.gitHubOwnerAndProject)
                        credentials(this.gitHubCredentials)
                    }
                    cloneTimeout 20
                    relativeTargetDir(this.gitHubCheckoutDir)
                }
            }

            steps {
                shell('makedir -p \$WORKSPACE/\$JOB_NAME;cd \$WORKSPACE/' + this.gitHubCheckoutDir + ';')
                ant {
                    targets([])
                    buildFile("\$WORKSPACE/" + this.antBuildFile)
                    antInstallation('Default')

                    props('version': "\$VERSION",
                            'base.source.dir': "\$WORKSPACE/" + this.antSourceDir,
                            'base.build.dir': "\$WORKSPACE/\$JOB_NAME",
                            'flex.sdk.dir': this.antFlexSdkDir,
                            'test.mode': "\$FLEX_TEST_MODE",
                            'compile.help.enabled': true,
                            'compile.debug': "\$FLEX_DEBUG",
                            'air.sdk.dir': this.antAirSdkDir,
                            'protocol.validator.skip': "\$PROTOCOL_VALIDATION_SKIP",
                            'protocol.validator.report.dir': "\$WORKSPACE/protocolValidationReport")
                }

                shell('cd \$WORKSPACE/\$JOB_NAME; rm tests -f -r; rm *.cache -f -r; zip -r ../FLEX.war ./;')

                if (antBuildTestFile) {
                    shell('killall Xvnc Xrealvnc || true; rm -fv /tmp/.X*-lock /tmp/.X11-unix/X*')
                    ant {

                        targets([])
                        buildFile("\$WORKSPACE/" + this.antBuildTestFile)
                        antInstallation('Default')
                        props(
                                'version': "\$VERSION",
                                'base.source.dir': "\$WORKSPACE/" + this.antSourceDir,
                                'base.build.dir': "\$WORKSPACE/\$JOB_NAME",
                                'flex.sdk.dir': this.antFlexSdkDir,
                                'test.mode': "\$FLEX_TEST_MODE",
                                'compile.help.enabled': true,
                                'compile.debug': "\$FLEX_DEBUG",
                                'air.sdk.dir': this.antAirSdkDir,
                                'tests.headless': true)

                    }
                    shell('killall Xvnc Xrealvnc || true;')
                }
            }

            publishers {
                archiveArtifacts {
                    pattern(this.artifacts)
                    onlyIfSuccessful()
                }
                if (this.emails) {
                    mailer this.emails.join(' ')
                }
            }
        }
    }
}
