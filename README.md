# dfinity-candid-lite
This is the lighter version of the [ic4j-agent](https://github.com/ic4j/ic4j-candid)

# Documentation
  From ic4j-candid version [0.7.4](https://repo1.maven.org/maven2/org/ic4j/ic4j-candid/0.7.4/), mobile users can also start building their application on top of ICP using the Java SDK.
  
  The Android Developer's preferred way to interact with the canister is via POJO class.
 
  For the demonstration purpose, I have built a Tweet's App on top of ICP. This can be found [here](https://github.com/nikhil5642/ICP-TweetApp)

  To start building an Android app on ICP, refer to [this](https://github.com/nikhil5642/ic4j-agent-lite) repo.

## Supported type mapping between Java and Candid

| Candid      | Java    |
| :---------- | :---------- | 
| bool   | Boolean | 
| int| BigInteger   | 
| int8   | Byte | 
| int16   | Short | 
| int32   | Integer | 
| int64   | Long | 
| nat| BigInteger   | 
| nat8   | Byte | 
| nat16   | Short | 
| nat32   | Integer | 
| nat64   | Long |
| float32   | Float, Double | 
| float64   | Double | 
| text   | String | 
| opt   | Optional | 
| principal   | Principal | 
| vec   | array, List | 
| record   | Map, Class | 
| variant   | Map, Enum | 
| func   | Func | 
| service   | Service | 
| null   |Null | 


## POJO Java class with Candid annotations

```
import java.math.BigInteger;

import org.ic4j.candid.annotations.Field;
import org.ic4j.candid.annotations.Name;
import org.ic4j.candid.types.Type;

public class Pojo {
	@Field(Type.BOOL)
	@Name("bar")
	public Boolean bar;

	@Field(Type.INT)
	@Name("foo")
	public BigInteger foo;
}
```

```
Pojo pojoValue = new Pojo();
				
pojoValue.bar = new Boolean(false);
pojoValue.foo = BigInteger.valueOf(43); 
				
```
# Build

You need JDK 8+ to build IC4J Candid.

