package graphQL.schemas

import org.parboiled2.Position
import sangria.ast.{NamedType, UnionTypeDefinition}
import sangria.schema._
import sangria.macros.derive.{DocumentField, Interfaces, ObjectTypeDescription, deriveObjectType}

/**
  * Created by User on 23.01.2017.
  */
case class Transport(
                      name: String,
                      label: String,
                      properties: List[Property]
                    )
object Transport {
  import Property._

  implicit val TransportType = ObjectType("Transport", fields[Unit, Transport](
    Field("name", StringType, resolve = _.value.name),
    Field("label", StringType, resolve = _.value.label),
    Field("properties", ListType(PropertyType), resolve = _.value.properties)
  ))

}


sealed trait Property extends Product {
  type T
  lazy val `type`: String = productPrefix.split("-") match {
    case Array(_, x) => x
    case Array(x) => x
    case _ => sys.error(s"illegal property $productPrefix")
  }

  def name: String

  def label: String

  def defaultValue: T
}


object Property {

  sealed trait EnumProperty extends Property {
    type E
    type T = Option[E]

    def enum: List[E]
  }

  case class bool(name: String, label: String, defaultValue: Boolean) extends Property {
    type T = Boolean
  }

  case class string(name: String, label: String, defaultValue: String) extends Property {
    type T = String
  }

  case class int(name: String, label: String, defaultValue: Int) extends Property {
    type T = Int
  }

  case class select_string(name: String, label: String, defaultValue: Option[String] = None, enum: List[String]) extends EnumProperty {
    type E = String
  }

  case class select_int(name: String, label: String, defaultValue: Option[Int] = None, enum: List[Int]) extends EnumProperty {
    type E = Int
  }
//  https://github.com/sangria-graphql/sangria/blob/e26ef09df838c8e5200a541377b944fec4ada7d4/src/test/scala/sangria/execution/UnionInterfaceSpec.scala
//  implicit val PropertyType = InterfaceType("Property", fields[Unit, Property](
//    Field("label", StringType, resolve = _.value.label)))

//  implicit val boolType: ObjectType[Unit, bool] = deriveObjectType[Unit, bool](Interfaces[Unit, bool](PropertyType))
//  implicit val stringType: ObjectType[Unit, string] = deriveObjectType[Unit, string](Interfaces[Unit, string](PropertyType))
//  implicit val intType: ObjectType[Unit, int] = deriveObjectType[Unit, int](Interfaces[Unit, int](PropertyType))
//  implicit val selectStringType: ObjectType[Unit, select_string] = deriveObjectType[Unit, select_string](Interfaces[Unit, select_string](PropertyType))
//  implicit val selectIntType: ObjectType[Unit, select_int] = deriveObjectType[Unit, select_int](Interfaces[Unit, select_int](PropertyType))
//


  implicit val boolType: ObjectType[Unit, bool] = deriveObjectType[Unit, bool]()
  implicit val stringType: ObjectType[Unit, string] = deriveObjectType[Unit, string]()
  implicit val intType: ObjectType[Unit, int] = deriveObjectType[Unit, int]()
  implicit val selectStringType: ObjectType[Unit, select_string] = deriveObjectType[Unit, select_string]()
  implicit val selectIntType: ObjectType[Unit, select_int] = deriveObjectType[Unit, select_int]()
  implicit val PropertyType: UnionType[Unit] = UnionType[Unit]("Property", types = stringType :: intType :: selectStringType :: selectIntType :: Nil)


}