$DataCollectorSet = new-object -COM Pla.DataCollectorSet 
$DataCollectorSet.Query("test",$null)
$DataCollectorset.OutputLocation
$DataCollectorSet.Start($TRUE)
Start-Sleep -s 30
$DataCollectorSet.Stop($TRUE)