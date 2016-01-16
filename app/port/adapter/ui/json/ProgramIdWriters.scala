package port.adapter.ui.json

import domain.model.program.ProgramId
import play.api.libs.json._

object ProgramIdWriters {

  implicit val programIdWriters = new Writes[ProgramId] {
    override def writes(id: ProgramId): JsValue = Json.toJson(id.value)
  }

}
