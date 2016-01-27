param([Int32]$diagDuration=10, [string]$diagName="test")

$DataCollectorSet = new-object -COM Pla.DataCollectorSet 
$DataCollectorSet.Query($diagName,$null)
$DataCollectorset.OutputLocation
$DataCollectorSet.Start($TRUE)
Start-Sleep -s $diagDuration
$DataCollectorSet.Stop($TRUE)