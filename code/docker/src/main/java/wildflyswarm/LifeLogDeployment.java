package wildflyswarm;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.keycloak.Secured;

public class LifeLogDeployment {

  public static JAXRSArchive deployment() {
    JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);

    archive.addPackages(true, "lifelog");
    archive.addAsWebInfResource(
      new ClassLoaderAsset("META-INF/persistence.xml", Bootstrap.class.getClassLoader()),
      "classes/META-INF/persistence.xml");
    archive.addAsWebInfResource(
      new ClassLoaderAsset("keycloak.json", Bootstrap.class.getClassLoader()),
      "keycloak.json");

    archive.as(Secured.class)
      .protect("/entries/*")
      .withMethod("POST", "PUT", "DELETE")
      .withRole("author");

    return archive;
  }

}
