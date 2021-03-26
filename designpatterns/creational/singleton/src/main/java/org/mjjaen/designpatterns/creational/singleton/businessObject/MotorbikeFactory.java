package org.mjjaen.designpatterns.creational.singleton.businessObject;

public class MotorbikeFactory {
	public static Motorbike createCar(SingletonImplementation singletonImplementation) {
		if(singletonImplementation.equals(SingletonImplementation.SINGLETONLAZY))
			return SingletonLazy.getInstance();
		else if(singletonImplementation.equals(SingletonImplementation.SINGLETONEAGER))
			return SingletonEager.getInstance();
		else if(singletonImplementation.equals(SingletonImplementation.SINGLETONBILLPUGH))
			return SingletonBillPugh.getInstance();
		else if(singletonImplementation.equals(SingletonImplementation.SINGLETONENUM))
			return SingletonEnum.INSTANCE;
		else if(singletonImplementation.equals(SingletonImplementation.SINGLETONTHREADSAFE))
			return SingletonThreadSafe.getInstance();
		else if(singletonImplementation.equals(SingletonImplementation.SINGLETONSTATICBLOCK))
			return SingletonStaticBlock.getInstance();
		else if(singletonImplementation.equals(SingletonImplementation.SINGLETONSERIALIZABLE))
			return SingletonSerializable.getInstance();
		else
			return null;
	}
}
