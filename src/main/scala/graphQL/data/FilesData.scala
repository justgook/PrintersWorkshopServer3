package graphQL.data

import graphQL.schemas.File

class FilesData {
  private val FileList = List(
    File("aa.stl", 1515),
    File("aa.stl", 1515213))

  def list: List[File] = FileList

}
