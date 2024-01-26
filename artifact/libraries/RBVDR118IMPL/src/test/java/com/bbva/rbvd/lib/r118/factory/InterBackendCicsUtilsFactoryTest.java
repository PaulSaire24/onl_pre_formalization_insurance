package com.bbva.rbvd.lib.r118.factory;

import com.bbva.elara.utility.interbackend.cics.InterBackendCicsUtilsFactory;
import com.bbva.elara.utility.interbackend.cics.InterBackendCicsUtils;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;

public class InterBackendCicsUtilsFactoryTest implements InterBackendCicsUtilsFactory{

	@Override
	public InterBackendCicsUtils getInterBackendCicsUtils(BundleContext arg0) {
		return Mockito.mock(InterBackendCicsUtils.class);
	}
}
