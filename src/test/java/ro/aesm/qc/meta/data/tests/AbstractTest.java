package ro.aesm.qc.meta.data.tests;

import ro.aesm.qc.api.base.IExecutionContext;
import ro.aesm.qc.base.ExecutionContext;

public abstract class AbstractTest {

	protected IExecutionContext createExecutionContext() {
		return new ExecutionContext("12345", "user-name");
	}

}
