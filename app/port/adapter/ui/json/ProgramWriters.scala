package port.adapter.ui.json

import domain.model.program.Program
import play.api.libs.json._
import port.adapter.ui.json.ChannelWriters.channelWriters
import port.adapter.ui.json.ProgramIdWriters.programIdWriters

object ProgramWriters {

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

}
