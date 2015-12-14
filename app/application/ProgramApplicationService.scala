package application

import java.time.LocalDate

import domain.model.channel.{ChannelId, Channel}
import domain.model.program.Program
import port.adapter.web.service.HttpProgramRepository

class ProgramApplicationService(val programRepository: HttpProgramRepository) {

  val kantoChannels = Seq(
    Channel(id = ChannelId(value = 1), name = "NHK総合"),
    Channel(id = ChannelId(value = 2), name = "NHK Eテレ"),
    Channel(id = ChannelId(value = 3), name = "フジテレビ"),
    Channel(id = ChannelId(value = 4), name = "日本テレビ"),
    Channel(id = ChannelId(value = 5), name = "TBS"),
    Channel(id = ChannelId(value = 6), name = "テレビ朝日"),
    Channel(id = ChannelId(value = 7), name = "テレビ東京"),
    Channel(id = ChannelId(value = 8), name = "tvk"),
    Channel(id = ChannelId(value = 7), name = "テレビ東京"),
    Channel(id = ChannelId(value = 8), name = "tvk"),
    Channel(id = ChannelId(value = 19), name = "TOKYO MX"),
    Channel(id = ChannelId(value = 20), name = "AT-X"),
    Channel(id = ChannelId(value = 21), name = "アニマックス")
  )

  def getProgramGuideOfKanto(date: LocalDate): Seq[Program] = {
    programRepository.allProgramsOfDate(date, kantoChannels)
  }

}
