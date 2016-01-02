package port.adapter.controllers

import domain.model.channel.{ChannelGroup, ChannelGroupId, ChannelGroupRepository, ChannelId}
import org.specs2.mock._
import org.specs2.mutable._
import play.api.http.MimeTypes._
import play.api.test.Helpers._
import play.api.test._

import scala.util.Try

class ChannelGroupControllerSpec extends Specification with Mockito {

  "ChannelGroupController" should {
    "return channel group list json" in new WithApplication() {
      val channelGroups = Seq(
        ChannelGroup(id = ChannelGroupId(value = 1), name = "テストチャンネル", channelIds = Seq(ChannelId(value = 1)))
      )
      val repository = mock[ChannelGroupRepository]
      val controller = new ChannelGroupController {
        override protected[this] def newChannelGroupRepository() = repository
      }

      repository.allChannelGroup() returns Try(channelGroups)

      val result = controller.list(FakeRequest(GET, "/group"))
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == JSON)
    }
  }

}
