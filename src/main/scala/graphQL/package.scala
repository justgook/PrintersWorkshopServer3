import graphQL.data.{FilesData, PrintersData}
import graphQL.schemas.Printer.Setting
import graphQL.schemas.{File, Printer}
import sangria.macros.derive.deriveObjectType
//import sangria.schema.{Argument, Field, ListType, ObjectType, OptionInputType, OptionType, Schema, StringType, fields}
import sangria.marshalling.ScalaInput.scalaInput
import sangria.schema._


package object graphQL {

  //  val Id = Argument("id", StringType)
  case class State(printers: PrintersData, files: FilesData)

  val QueryType = ObjectType("Query", fields[State, Unit](
    //    Field("product", OptionType(ProductType),
    //      description = Some("Returns a product with specific `id`."),
    //      arguments = Id :: Nil,
    //      resolve = c => c.ctx.product(c arg Id)),

    Field("files", ListType(File.PrinterType),
      description = Some("Returns a list of all available files."),
      resolve = _.ctx.files.list
    ),

    Field("printers", ListType(Printer.PrinterType),
      description = Some("Returns a list of all available printer."),
      resolve = _.ctx.printers.list
    )
  ))


  //======================================================================================================================
//  val CreateHumanCommand = InputObjectType("CreateHuman", "A command that creates human being", List(
//    InputField("name", StringType, Some("The _one_ and **only**")),
//    InputField("friends", OptionInputType(ListInputType(StringType)), defaultValue = Some(Nil)),
//    InputField("appearsIn", OptionInputType(ListInputType(EpisodeEnum)), defaultValue = Some(Nil)),
//    InputField("money", OptionInputType(BigDecimalType), defaultValue = Some(BigDecimal(0))),
//    InputField("homePlanet", OptionInputType(StringType))
//  ))
//
//  case class ValidationError(message: String) extends Exception(message) with UserFacingError
//
//  val Mutation = ObjectType("Mutation", fields[CharacterRepo, Unit](
//    Field("addHuman", OptionType(Human), Some("Creates a new `human` being!"),
//      arguments = Argument("command", CreateHumanCommand) :: Nil,
//      resolve = ctx => {
//        val command = ctx.arg[Map[String, Any]]("command")
//
//        val human = models.Human(
//          id = UUID.randomUUID.toString,
//          name = Some(command("name").asInstanceOf[String]),
//          friends = command("friends").asInstanceOf[List[String]],
//          appearsIn = command("appearsIn").asInstanceOf[List[Episode.Value]],
//          money = command("money").asInstanceOf[BigDecimal],
//          homePlanet = command.get("homePlanet").asInstanceOf[Option[String]])
//
//        if (ctx.ctx.getHumans.exists(_.name == human.name))
//          throw new ValidationError(s"Human with name '${human.name.get}' already exists!")
//
//        ctx.ctx.addHuman(human)
//        human
//      })
//  ))


  val MutationType = ObjectType("Mutation", fields[State, Unit](
    Field("addPrinter", OptionType(Printer.PrinterType), Some("Creates a new `human` being!"),
      arguments = Argument("id", StringType) :: Nil,
      resolve = c => {
        val id = c.arg[String]("id")
        val transport = List(Setting("transportName", "Demo"), Setting("baud", "31"))
        val printer = Printer(
          name = s"new Printer $id",
          transport = transport
        )
        c.ctx.printers.addPrinter(printer)
      })
  ))


  //======================================================================================================================

  val myDirective = Directive("myDirective",
    description = Some("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec posuere ornare nulla, non bibendum nisi dictum at. Etiam consequat velit ut leo fringilla mollis. Integer ut fringilla ante. Curabitur sagittis malesuada nibh sed vestibulum. \nNunc eu metus felis. Cras tellus nibh, porta nec lorem quis, elementum egestas tellus. Etiam vitae tellus vitae dui varius lobortis."),
    arguments =
      Argument("first", OptionInputType(ListInputType(StringType)), "Some descr", scalaInput(List("foo", "bar", "baz"))) ::
        Argument("last", OptionInputType(IntType), "Another descr") ::
        Nil,
    locations = Set(DirectiveLocation.FieldDefinition, DirectiveLocation.InputFieldDefinition, DirectiveLocation.Field),
    shouldInclude = _ => true)

  val deferDirective = Directive("defer",
    description = Some("Specify that some part of the query can arrive later"),
    locations = Set(DirectiveLocation.Field),
    shouldInclude = _ => false)

  //======================================================================================================================

  val schema = Schema(QueryType, Some(MutationType), directives = BuiltinDirectives :+ myDirective :+ deferDirective)
  val state = State(printers = new PrintersData, files = new FilesData)

}
