package port.adapter.controllers

import java.time.LocalDate

import application.ProgramApplicationService
import domain.model.channel.{Channel, ChannelId}
import domain.model.program.{Program, ProgramId}
import play.api.libs.json._
import play.api.mvc._
import port.adapter.web.service.HttpProgramRepository

class ProgramController extends Controller {

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
  val programApplicationService = new ProgramApplicationService(programRepository)

  def list = Action {
    val programs = programApplicationService.getProgramGuideOfKanto(LocalDate.now())
    Ok(Json.toJson(programs))
  }

}
