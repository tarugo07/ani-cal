package port.adapter.controllers

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import application.ProgramApplicationService
import domain.model.channel.{Channel, ChannelGroupRepository, ChannelId, ChannelRepository}
import domain.model.program.{Program, ProgramId, ProgramRepository}
import play.api.libs.json._
import play.api.mvc._
import port.adapter.web.service.{HttpChannelGroupRepository, HttpChannelRepository, HttpProgramRepository}

import scala.util.Try

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

  val channelRepository = newChannelRepository()
  val channelGroupRepository = newChannelGroupRepository()
  val programRepository = newProgramRepository()

  val programApplicationService = new ProgramApplicationService(channelRepository, channelGroupRepository, programRepository)

  protected[this] def newChannelRepository(): ChannelRepository = new HttpChannelRepository

  protected[this] def newChannelGroupRepository(): ChannelGroupRepository = new HttpChannelGroupRepository

  protected[this] def newProgramRepository(): ProgramRepository = new HttpProgramRepository

  def list(date: String, groupId: Long) = Action {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val programs = {
      for {
        searchDate <- Try {
          LocalDate.parse(date, formatter)
        }
        programs <- programApplicationService.getProgramGuide(searchDate, groupId)
      } yield programs
    }.getOrElse(Seq.empty)
    Ok(Json.toJson(programs))
  }

}
