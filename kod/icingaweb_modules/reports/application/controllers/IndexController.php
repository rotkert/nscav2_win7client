<?php

use Icinga\Web\Controller\ModuleActionController;

class Reports_IndexController extends ModuleActionController
{
    public function indexAction()
    {
	$servername = "localhost";
	$username = "root";
	$password = "root";
	$dbname = "icinga2idomysql";

	// Create connection
	$conn = mysqli_connect($servername, $username, $password, $dbname);
	// Check connection
	if (!$conn) {
    	    die("Connection failed: " . mysqli_connect_error());
	}

	$sql = "SELECT sh.state_time, sh.output, o.name1 FROM icinga_statehistory sh join icinga_objects o on o.object_id = sh.object_id where sh.object_id = 166 and sh.state = 2 order by sh.statehistory_id desc";
	$result = mysqli_query($conn, $sql);

	$returnHtml = "<div class='content'><table id='reportsTable'  class='action'>";

	if (mysqli_num_rows($result) > 0) {
    	    // output data of each row
    	    while($row = mysqli_fetch_assoc($result)) {
		$reportName = explode("=", $row["output"])[1];
             	$returnHtml .= "<tr class='state critical' href=''><td class='state'><strong>" . $row["name1"] . "</strong><br/>" . $row["state_time"] . "</td>";
		$returnHtml .= "<td><a href=javascript:showReport('" . $reportName . "')>" . $reportName . "</a></td></tr>";
    	    }
	    $returnHtml .= "</table></div>";
    	} else {
    	    $returnHtml .= "</table></div>";
    	}
	echo $returnHtml;

    	mysqli_close($conn);
    }
}
