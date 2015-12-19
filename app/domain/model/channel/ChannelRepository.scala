package domain.model.channel

trait ChannelRepository {

  def allChannels(): Seq[Channel]

  def allChannelsOfGroup(group: ChannelGroup): Seq[Channel]

}
