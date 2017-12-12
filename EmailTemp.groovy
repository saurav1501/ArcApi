<html>
<body>
<%
    if(build.testResultAction) {
        def testResult = build.testResultAction
        def testCount = String.format("%.2f",(testResult.totalCount))
        def testPassed = String.format("%.2f",())
        def testFailed = String.format("%.2f",(testResult.result.failCount))
        def testSkipped = String.format("%.2f",())
        def buildDuration = String.format("%.2f",(testResult.result.duration ))
    }
%>
Hi All,<br><br>

The execution of the Automation suite has been completed and the results are as below.<br><br>

<b><u>Configuration :</u></b><br>
Project Name : $JOB_NAME<br>
Test Server URL : $Test_Server_URL<br>
Group Name : $Group_Name<br><br>

<b><u>Execution Results :</u></b><br>
Status : <font color="blue">$BUILD_STATUS</font><br>
Tests run : $testCount<br>
Failures : $testFailed<br>
Errors : 0<br>
Skipped : 0<br>
Total time : $buildDuration<br>
Finished at: Tue May 06 17:12:19 IST 2014<br>
Build URL : $BUILD_URL<br><br>

The HTML version of the automation results and the log file is attached with this e-mail for your reference.<br><br>

Regards,<br>
Jenkins CI Tool
</body>
</html>