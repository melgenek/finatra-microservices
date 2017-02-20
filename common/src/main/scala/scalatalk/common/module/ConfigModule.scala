package scalatalk.common.module

import com.github.racc.tscg.TypesafeConfigModule
import com.twitter.inject.{Logging, TwitterModule}
import com.typesafe.config.ConfigFactory

object ConfigModule extends TwitterModule with Logging {

	override def configure(): Unit = {
		info("Loading Config file")
		val config = ConfigFactory.load()
		info(s"App Name from Config: ${config.getString("package.name")}")
		install(TypesafeConfigModule.fromConfigWithPackage(config, ""))
	}

}