import com.dslexample.FlexCiJobBuilder

new FlexCiJobBuilder(
        name: "new-test-flex-shit",
        description: "test-new",
        gitHubCredentials: "31df12ac-5d1f-495d-99fe-ad351505d316",
        gitHubCheckoutDir: "setretail10",
        gitHubOwnerAndProject: "crystalservice/setretail10",
        antBuildFile: "setretail10/SetRetail10_Server_GUI/build.xml",
        antSourceDir: "setretail10/SetRetail10_Server_GUI",
        antFlexSdkDir: "/opt/flexsdk",
        antAirSdkDir: "/opt/airsdk",
        antBuildTestFile: ""
).build(this)

new FlexCiJobBuilder(
        name: "new-test-flex-unit-shit",
        description: "test-new",
        gitHubCredentials: "31df12ac-5d1f-495d-99fe-ad351505d316",
        gitHubCheckoutDir: "setretail10",
        gitHubOwnerAndProject: "crystalservice/setretail10",
        antBuildFile: "setretail10/SetRetail10_Server_GUI/build.xml",
        antSourceDir: "setretail10/SetRetail10_Server_GUI",
        antFlexSdkDir: "/opt/flexsdk",
        antAirSdkDir: "/opt/airsdk",
        antBuildTestFile: "setretail10/SetRetail10_Server_GUI/build_tests.xml"
).build(this)