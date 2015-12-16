package application

import java.time.LocalDate

import domain.model.channel.{ChannelId, ChannelRepository}
import domain.model.program.{Program, ProgramRepository}

class ProgramApplicationService
(val channelRepository: ChannelRepository,
 val programRepository: ProgramRepository) {

  val channelIds = Seq(
    ChannelId(value = 1), ChannelId(value = 2), ChannelId(value = 3),
    ChannelId(value = 4), ChannelId(value = 5), ChannelId(value = 6),
    ChannelId(value = 7), ChannelId(value = 8), ChannelId(value = 19)
  )

  def getProgramGuideOfKanto(date: LocalDate): Seq[Program] = {
    val channels = channelRepository.allChannels().filter(ch => channelIds.contains(ch.id))
    programRepository.allProgramsOfDate(date, channels)
  }

}
