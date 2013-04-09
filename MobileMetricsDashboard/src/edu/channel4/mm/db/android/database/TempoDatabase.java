package edu.channel4.mm.db.android.database;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.model.description.AttributeDescription;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.model.description.EventNameDescription;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;

/**
 * BS temp database. This is just a singleton containing a bunch of ArrayLists
 * of models.
 * 
 * @author girum & mark
 */
@Singleton
public class TempoDatabase {

   @Inject private List<AppDescription> appDescriptions;
   @Inject private List<AttributeDescription> attributeDescriptions;
   @Inject private List<EventDescription> eventDescriptions;
   @Inject private List<EventNameDescription> eventNameDescriptions;
   @Inject private List<GraphRequest> customGraphRequests;
   private Graph graph = null;

   public void setAppDescriptions(List<AppDescription> appDescriptions) {
      this.appDescriptions.clear();
      this.appDescriptions.addAll(appDescriptions);
   }

   public void setAttributeDescriptions(
            List<AttributeDescription> attributeDescriptions) {
      this.attributeDescriptions.clear();
      this.attributeDescriptions.addAll(attributeDescriptions);
   }

   public void setEventDescriptions(List<EventDescription> eventDescriptions) {
      this.eventDescriptions.clear();
      this.eventDescriptions.addAll(eventDescriptions);
   }

   public void setEventNameDescriptions(
            List<EventNameDescription> eventNameDescriptions) {
      this.eventNameDescriptions.clear();
      this.eventNameDescriptions.addAll(eventNameDescriptions);
   }

   public void addCustomGraphRequest(CustomGraphRequest customGraphRequest) {
      this.customGraphRequests.add(customGraphRequest);
   }

   public List<GraphRequest> getCustomGraphRequests() {
      return customGraphRequests;
   }

   public void setGraph(Graph graph) {
      this.graph = graph;
   }

   public Graph getGraph() {
      return graph;
   }
}
