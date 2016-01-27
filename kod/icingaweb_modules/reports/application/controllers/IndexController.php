<?php

use Icinga\Web\Controller\ModuleActionController;

class Reports_IndexController extends ModuleActionController
{
    public function indexAction()
    {

	$servername = "localhost";
	$username = "root";
	$password = "root";
	$dbname = "icinga2";

	// Create connection
	$conn = mysqli_connect($servername, $username, $password, $dbname);
	// Check connection
	if (!$conn) {
    	    die("Connection failed: " . mysqli_connect_error());
	}

	$sql = "SELECT sh.state_time, sh.output, o.name1 FROM icinga_statehistory sh join icinga_objects o on o.object_id = sh.object_id where sh.object_id = 99 and sh.state = 2 order by sh.statehistory_id desc";
	$result = mysqli_query($conn, $sql);

	$returnHtml = "<div class='content'><table id='reportsTable' class='state-table' style='border-collapse:separate; border-spacing:1px'>";

	if (mysqli_num_rows($result) > 0) {
    	    // output data of each row
    	    while($row = mysqli_fetch_assoc($result)) {
		$reportName = explode("=", $row["output"])[1];
             	$returnHtml .= "<tr><td class='state-col state-ok' style='background-color:#f56; color:#fff; padding:0.333em;'>"
				  . "<div class='state-label'></div>" 
				  . "<div class='state-meta'><span class='relative-time time-ago'>" . $row["state_time"] . "</span></div></td>";
		$returnHtml .= "<td><a href='/icingaweb2/monitoring/host/show?host=windows7'>Windows7: </a>";
		$returnHtml .= "<a href='/icingaweb2/monitoring/service/show?host=windows7&service=diagnostics'>diagnostics<a>";
		$returnHtml .= "<a href=javascript:void(0); onclick=javascript:showReport('" . $reportName . "')>
				<p class='overview-plugin-output'>" . $reportName . "</p></a></td></tr>";
    	    }
	    $returnHtml .= "</table></div>";
    	} else {
    	    $returnHtml .= "</table></div>";
    	}
	echo $returnHtml;

    	mysqli_close($conn);
    }
}
