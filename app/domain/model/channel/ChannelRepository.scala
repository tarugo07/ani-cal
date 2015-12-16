package domain.model.channel

trait ChannelRepository {

  def allChannels(): Seq[Channel]

}
