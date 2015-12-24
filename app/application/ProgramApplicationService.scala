package application

import java.time.LocalDate

import domain.model.channel.{ChannelGroupId, ChannelGroupRepository, ChannelRepository}
import domain.model.program.{Program, ProgramRepository}

class ProgramApplicationService
(val channelRepository: ChannelRepository,
 val channelGroupRepository: ChannelGroupRepository,
 val programRepository: ProgramRepository) {

  def getProgramGuide(date: LocalDate, groupId: Long): Seq[Program] = {
    val group = channelGroupRepository.channelGroupOfId(ChannelGroupId(value = groupId))
    val channels = channelRepository.allChannelsOfGroup(group)
    programRepository.allProgramsOfDate(date, channels)
  }

}
