package port.adapter.ui.controllers

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import application.ProgramApplicationService
import domain.model.channel.{ChannelGroupRepository, ChannelRepository}
import domain.model.program.ProgramRepository
import play.api.libs.json._
import play.api.mvc._
import port.adapter.service.{HttpChannelGroupRepository, HttpChannelRepository, HttpProgramRepository}
import port.adapter.ui.json.ProgramWriters.programWriters

import scala.util.Try

class ProgramController extends Controller {

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
