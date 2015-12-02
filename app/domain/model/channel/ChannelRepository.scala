package domain.model.channel

import scala.util.Try

trait ChannelRepository {

  def resolve(id: ChannelId): Try[Option[Channel]]

}
