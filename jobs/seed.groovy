// If you want, you can define your seed job in the DSL and create it via the REST API.
// See https://github.com/sheehan/job-dsl-gradle-example#rest-api-runner

job('seed') {
    scm {
        github 'tjlee/job-dsl-gradle-example'
    }
//    triggers {
////        scm 'H/5 * * * *'
//    }
    steps {
        dsl {
            external 'jobs/**/example7Jobs.groovy'
            additionalClasspath 'src/main/groovy'
        }
    }
//    publishers {
//        archiveJunit 'build/test-results/**/*.xml'
//    }
}