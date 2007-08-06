/*
* Copyright 2007 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package injecto.dynamicmethod;
import java.util.regex.Pattern

/**
 * Represents a mapping between a dispatch handler and an incoming request.
 * 
 * Used as entries in the DynamicDispatchTable
 * @see DynamicDispatchTable
 */
class DynamicDispatchMapping 
{
	Pattern pattern
	String name
	int precedence
	Class[] argTypes
}