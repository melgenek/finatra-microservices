package scalatalk.common.module

import com.twitter.inject.TwitterModule

object BasePackageConfig {

	def apply(packageName: String): TwitterModule = new TwitterModule {
		flag(name = "package.name", default = packageName, help = "Package name")
	}

}
