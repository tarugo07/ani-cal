package infrastructure.http

import java.time.LocalDateTime

case class Program
(channelId: Int,
 channelName: String,
 title: String,
 subTitle: String,
 start: LocalDateTime,
 end: LocalDateTime)
