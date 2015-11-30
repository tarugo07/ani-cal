package infrastructure.http

trait ProgramReadingService {

  def getAllProgram(): Seq[Program]

}
