package application

import java.time.LocalDate

import domain.model.channel.{ChannelGroupId, ChannelGroupRepository, ChannelRepository}
import domain.model.program.{Program, ProgramRepository}

import scala.util.Try

class ProgramApplicationService
(val channelRepository: ChannelRepository,
 val channelGroupRepository: ChannelGroupRepository,
 val programRepository: ProgramRepository) {

  def getProgramGuide(date: LocalDate, groupId: Long): Try[Seq[Program]] = {
    for {
      group <- channelGroupRepository.channelGroupOfId(ChannelGroupId(value = groupId))
      channels <- channelRepository.allChannelsOfGroup(group)
      programs <- programRepository.allProgramsOfDate(date, channels)
    } yield programs
  }

}
