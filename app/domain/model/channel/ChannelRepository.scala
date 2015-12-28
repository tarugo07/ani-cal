package domain.model.channel

import scala.util.Try

trait ChannelRepository {

  def allChannels(): Try[Seq[Channel]]

  def allChannelsOfGroup(group: ChannelGroup): Try[Seq[Channel]]

}
