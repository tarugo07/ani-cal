package domain.model.program

import java.time.LocalDateTime

import domain.model.channel.Channel

case class Program(id: ProgramId, channel: Channel, name: String, title: String, episode: Int, startTime: LocalDateTime, endTime: LocalDateTime)
