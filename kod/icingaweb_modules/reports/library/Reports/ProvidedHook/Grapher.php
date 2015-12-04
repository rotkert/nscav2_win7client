<?php

namespace Icinga\Module\Reports\ProvidedHook;

use Icinga\Application\Config;
use Icinga\Exception\ConfigurationError;
use Icinga\Application\Hook\GrapherHook;
use Icinga\Module\Monitoring\Object\MonitoredObject;
use Icinga\Module\Monitoring\Object\Host;
use Icinga\Module\Monitoring\Object\Service;
use Icinga\Web\Url;

class Grapher extends GrapherHook
{
    protected $hasPreviews = true;

    public function has(MonitoredObject $object)
    {
        return true;
    }

    public function getPreviewHtml(MonitoredObject $object)
    {
	if ($object instanceof Host)
	   return '';
	if ($object->service_display_name != 'diagnostics')
	   return '';

        $output = $object->service_output;
	$reportName = explode('=', $output)[1];
	
	$html = '<table class="name-value-table"><tr><th>Report generated</th>';
        $html .= '<td><a href="/reports/' . $reportName . '.html" target="_blank" class="action-link">Show report</a></td>';
        $html .= '</tr></table>';
	return $html;
    }
}
