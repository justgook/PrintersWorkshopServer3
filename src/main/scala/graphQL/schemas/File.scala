package graphQL.schemas

import sangria.macros.derive.deriveObjectType
import sangria.schema.ObjectType

case class File(name: String, bytes: Int)

object File {
  implicit val PrinterType: ObjectType[Unit, File] =
    deriveObjectType[Unit, File]()
}
