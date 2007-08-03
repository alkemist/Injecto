package injecto;
import injecto.annotation.*
import injecto.InjectoPropertyStorage

class InjectoTest extends GroovyTestCase {
	
	def instance
	
	InjectoTest()
	{
		Injecto.inject(ExampleInjectee,ExampleInjecto)
 		instance = new ExampleInjectee()
	}
	
	void testDependencyHandling()
	{
		assert(Injecto.isInjected(ExampleInjectee, ExampleInjecto))
		assert(Injecto.isInjected(ExampleInjectee, OtherInjecto))
		assert(Injecto.isInjected(ExampleInjectee, DynamicMethodExampleInjecto))
	}
	
	void testAttachment()
	{
		assert(ExampleInjectee.metaClass.hasMetaMethod("getObjectProperty"))
		assert(ExampleInjectee.metaClass.hasMetaMethod("getStaticProperty"))
	}
	
	void testInjectAs()
	{
		assert(ExampleInjectee.metaClass.hasMetaMethod("aliasedMethod"))
		assert(ExampleInjectee.metaClass.hasMetaMethod("aliasedStaticMethod"))
	}
	
	void testProperties()
	{
		assertEquals("aaa", ExampleInjectee.newInstance().literalString)
	}
	
	void testGettersSettersAndPropertyStorage()
	{
		def g1 = new ExampleInjectee()
		def g2 = new ExampleInjectee()
		
		g1.objectProperty = "g1"
		assertEquals("g1", g1.objectProperty) 
		
		g2.objectProperty = "g2"
		assertEquals("g2", g2.objectProperty) 
		
		assertEquals("12345", ExampleInjectee.getStaticProperty()) // Value set in ExampleInjecto postGynamize
		ExampleInjectee.setStaticProperty("54321")
		assertEquals("54321", ExampleInjectee.getStaticProperty()) // Value set in ExampleInjecto postGynamize
	}
	
	void testDynamicMethodDispatch()
	{
		assertEquals(1, instance.dynamicMethodXXX())
	}
}

class ExampleInjectee {}

@InjectoDependencies([OtherInjecto, DynamicMethodExampleInjecto])
class ExampleInjecto 
{
	@InjectoProperty
	def objectProperty

	@InjectoProperty
	@InjectAs("staticProperty")
	static staticWrongNameProperty
	
	def literalString = "aaa"
	
	static void postInject(Class clazz)
	{
		clazz.setStaticProperty('12345')
	}
}

class OtherInjecto {

	@InjectAs("aliasedMethod")
	def otherInjectoMethod = {

	}

	@InjectAs("aliasedStaticMethod")
	static otherInjectoStaticMethod = {

	}
	
}

class DynamicMethodExampleInjecto {

	@InjectoDynamicMethod(
		pattern = "^dynamicMethodX(.+)",
		precedence = 1
	)
	def dynamicMethodMissingHandler = { String methodName, Object[] args ->
		return 1
	}
	
	@InjectoDynamicMethod(
		pattern = "^dynamicMethodZZ(.+)",
		precedence = 2
	)
	def lowerPrecedenceDynamicMethodMissingHandler = { String methodName, Object[] args ->
		return 2
	}
}