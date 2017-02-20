package scalatest.common

import com.twitter.inject.server.FeatureTest


abstract class FinatraFeatureTest extends FeatureTest {

	protected override def afterEach(): Unit = {
		super.afterEach()
		server.close()
	}

}
