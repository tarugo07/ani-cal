package port.adapter.ui.json

import domain.model.channel.Channel
import play.api.libs.json._
import port.adapter.ui.json.ChannelIdWriters.channelIdWriters

object ChannelWriters {

  implicit val channelWriters = new Writes[Channel] {
    override def writes(channel: Channel): JsValue = {
      Json.obj(
        "id" -> channel.id,
        "name" -> channel.name
      )
    }
  }

}
