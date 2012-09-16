package presentation;

import java.util.List;

import abstraction.Area;
import abstraction.Resource;
import abstraction.Resource.ResourceType;

public interface IHM {

	public Resource selectResourceFromList(List<Resource> list);

	public Area selectAreaFromList(List<Area> list);

	public void warnSendWorker(int i, ResourceType minerals);

}
