package infrastructure.service

case class Program(channelId: Int, channelName: String, title: String, subTitle: String, start: BigInt, end: BigInt)

trait ProgramReadingService {

  def readProgram(): Seq[Program]

}
