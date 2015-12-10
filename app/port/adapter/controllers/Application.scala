package port.adapter.controllers

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

  val repository = new HttpProgramRepository

  def index = Action {
    val programs = repository.allPrograms()
    Ok(Json.toJson(programs))
  }

}
