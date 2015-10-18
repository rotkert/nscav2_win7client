package inz.data.warehouse;

import inz.data.collectors.CriticalEvent;

public abstract class Warehouse
{
	public abstract CriticalEvent processMeasure();
	public abstract void reset();
}
