package port.adapter.ui.json

import domain.model.channel.ChannelId
import play.api.libs.json._

object ChannelIdWriters {

  implicit val channelIdWriters = new Writes[ChannelId] {
    override def writes(id: ChannelId): JsValue = Json.toJson(id.value)
  }

}
