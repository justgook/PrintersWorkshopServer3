package graphQL.schemas

import sangria.macros.derive._
import sangria.schema._

case class Printer(
                    name: String,
                    transport: List[Printer.Setting],
                    status: Printer.Status = Printer.Status()
                  )

object Printer {

  case class Setting(key: String, value: String)

  case class PrintingProgress(total: Int, printed: Int)

  case class Status(
                     online: Boolean = false,
                     temperature: List[Int] = List(0),
                     progress: PrintingProgress = PrintingProgress(0, 0),
                     file: Option[File] = None
                   )

//  object Status {
//    http://sangria-graphql.org/learn/#enumtype-derivation
//    sealed trait Fruit
//
//    case object RedApple extends Fruit
//
//    case object SuperBanana extends Fruit
//
//    case object MegaOrange extends Fruit
//
//    sealed abstract class ExoticFruit(val score: Int) extends Fruit
//
//    case object Guave extends ExoticFruit(123)
//
//    val FruitType = deriveEnumType[Fruit](
//      EnumTypeName("Foo"),
//      EnumTypeDescription("It's foo"))
//  }

  //https://github.com/sangria-graphql/sangria/blob/e26ef09df838c8e5200a541377b944fec4ada7d4/src/test/scala/sangria/execution/UnionInterfaceSpec.scala
  //  val PetType = UnionType[Unit]("Pet", types = DogType :: CatType :: Nil)
  implicit val PrintingProgressType: ObjectType[Unit, PrintingProgress] = deriveObjectType[Unit, PrintingProgress](
    ObjectTypeDescription("Progress of print"),
    DocumentField("total", "Total Count of lines "),
    DocumentField("printed", "Already printed line count")
  )
  implicit val StatusType: ObjectType[Unit, Status] = deriveObjectType[Unit, Status]()
  //TODO add descriptions
  implicit val SettingType: ObjectType[Unit, Setting] = deriveObjectType[Unit, Setting]()
  //TODO add descriptions
  implicit val PrinterType: ObjectType[Unit, Printer] =
    deriveObjectType[Unit, Printer](
      ObjectTypeDescription("Active 3d printer"),
      DocumentField("name", "User defined name of printer "),
      DocumentField("transport", "List of settings that will be used to connect printer")
    )
}
