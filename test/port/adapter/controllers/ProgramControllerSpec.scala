package port.adapter.controllers

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

import domain.model.channel._
import domain.model.program.{Program, ProgramId, ProgramRepository}
import org.specs2.mock._
import org.specs2.mutable._
import play.api.http.MimeTypes._
import play.api.test.Helpers._
import play.api.test._

import scala.util.Try

class ProgramControllerSpec extends Specification with Mockito {

  "ProgramController" should {
    "return program list json" in new WithApplication() {
      val date = "20160101"
      val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
      val searchDate = LocalDate.parse(date, formatter)
      val channel = Channel(id = ChannelId(value = 1), name = "テストチャンネル")
      val channelGroup = ChannelGroup(
        id = ChannelGroupId(value = 1),
        name = "テストチャンネルグループ",
        channelIds = Seq(ChannelId(value = 1))
      )
      val program = Program(
        id = ProgramId(value = 1),
        channel = channel,
        name = "テストプログラム",
        title = "テストタイトル",
        episode = 1,
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now().plusMinutes(30)
      )

      val channelRepository = mock[ChannelRepository]
      val channelGroupRepository = mock[ChannelGroupRepository]
      val programRepository = mock[ProgramRepository]

      channelGroupRepository.channelGroupOfId(channelGroup.id) returns Try(channelGroup)
      channelRepository.allChannelsOfGroup(channelGroup) returns Try(Seq(channel))
      programRepository.allProgramsOfDate(searchDate, Seq(channel)) returns Try(Seq(program))

      val controller = new ProgramController {
        override protected[this] def newChannelRepository(): ChannelRepository = channelRepository

        override protected[this] def newChannelGroupRepository(): ChannelGroupRepository = channelGroupRepository

        override protected[this] def newProgramRepository(): ProgramRepository = programRepository
      }

      val result = controller.list(date, channelGroup.id.value)(FakeRequest(GET, "/programs"))
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == JSON)
    }
  }

}
