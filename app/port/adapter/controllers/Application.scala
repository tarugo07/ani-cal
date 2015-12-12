package port.adapter.controllers

import java.time.LocalDate

import domain.model.channel.{Channel, ChannelId}
import domain.model.program.{Program, ProgramId}
import play.api.libs.json._
import play.api.mvc._
import port.adapter.web.service.HttpProgramRepository

class Application extends Controller {

  implicit val channelIdWriters = new Writes[ChannelId] {
    override def writes(id: ChannelId): JsValue = Json.toJson(id.value)
  }

  implicit val programIdWriters = new Writes[ProgramId] {
    override def writes(id: ProgramId): JsValue = Json.toJson(id.value)
  }

  implicit val channelWriters = new Writes[Channel] {
    override def writes(channel: Channel): JsValue = {
      Json.obj(
        "id" -> channel.id,
        "name" -> channel.name
      )
    }
  }

  implicit val programWriters = new Writes[Program] {
    override def writes(program: Program): JsValue = {
      Json.obj(
        "id" -> program.id,
        "channel" -> program.channel,
        "name" -> program.name,
        "title" -> program.title,
        "start_time" -> program.startTime.toString,
        "end_time" -> program.endTime.toString
      )
    }
  }

  val programRepository = new HttpProgramRepository
  val channels = Seq(
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

  def index = Action {
    val programs = programRepository.allPrograms(LocalDate.now(), channels)
    Ok(Json.toJson(programs))
  }

}
