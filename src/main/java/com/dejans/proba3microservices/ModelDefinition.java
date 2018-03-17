package com.dejans.proba3microservices;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.Class;
import org.apache.commons.lang3.reflect.FieldUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ModelDefinition {
    private String openapi;
    private Info info;
    private List<Url> servers;
    private Map<String, Method> paths;
    private Components components;

    public String toCheck() throws IOException {
        String pom = new String();
        Object obj = components.getSchemas().get("Pet");
        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in String
        pom += mapper.writeValueAsString(obj);
        SchemaObject sc = new SchemaObject();

        ObjectMapper mapperRead = new ObjectMapper();
        mapperRead.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //JSON from String to Object
        sc = mapperRead.readValue(pom, SchemaObject.class);

        return pom + "\n" + sc.toString(10);
    }

    public String toString() {
        String pom = new String();

        pom = "<p><pre>";
        pom += "OpenApi:" + openapi + "\n";
        if (info != null) {
            pom += "info:\n";
            pom += "  version:" + info.getVersion() + "\n";
            pom += "  title:" + info.getTitle() + "\n";
            pom += "  licence:\n";
            if (info.getlicense() != null) {
                pom += "     name:" + info.getlicense().name + "\n";
            }
        }

        if (servers != null) {
            pom += "Servers:\n";
            for (Url url : servers) {
                pom += "  url:" + url.getUrl() + "\n";
            }
        }

        if (paths != null) {
            pom += "paths:\n";
            for (Map.Entry entry : paths.entrySet()) {
                Method val = (Method) entry.getValue();
                pom += " " + entry.getKey() + ":\n";
                pom += val.toString(2);
            }
        }
        if (components != null) {
            pom += "components :\n";
            if (components.getSchemas() != null) {
                for (Map.Entry<String, Object> comentry : components.getSchemas().entrySet()) {
                    //Object objClass = components.get(entry.getKey());
                    Object objClass = components.getSchemas().get(comentry.getKey());
                    Map mObjClass = (Map) objClass;
                    Class classLocal = objClass.getClass();
                    pom += " " + comentry.getKey() + ":\n";
                    pom += " " + comentry.getValue() + ":\n";

                    //Get All public fields
                    Field[] allFields = FieldUtils.getAllFields(classLocal);
                    for (Field filParam : allFields) {
                        filParam.setAccessible(true);
                        try {
                            Object obj = filParam.get(objClass);
                            Type typ = filParam.getGenericType();
                            if (obj != null) {
                                pom += "ObjClass:" + obj.toString() + " Field: " + filParam.toString() + "\n";
                                pom += "Type:" + typ.toString() + "\n";
                            } else {
                                pom += "ObjClass NULL:" + filParam.toString() + "\n";
                            }

                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    //prints public fields of ConcreteClass, it's superclass and super interfaces
                    //System.out.println(Arrays.toString(publicFields));
                    pom += Arrays.toString(allFields) + "\n";
                }
            }

            Class param = Parameters.class;
            Parameters parObj = new Parameters("Kupus", true);

            Field[] fil = param.getDeclaredFields();
            for (Field filParam : fil) {
                filParam.setAccessible(true);
                try {
                    Object obj = filParam.get(parObj);
                    if (obj != null) {
                        pom += "ParObj:" + obj.toString() + " " + filParam.toString() + "\n";
                    } else {
                        pom += "ParObj NULL:" + filParam.toString() + "\n";
                    }

                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            pom += Arrays.toString(fil);

        }

        pom += "</pre></p>";

        return pom;

    }

    public String getOpenapi() {
        return this.openapi;
    }

    public void setOpenapi(String openapi) {
        this.openapi = openapi;
    }

    public Info getInfo() {
        return this.info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Url> getServers() {
        return this.servers;
    }

    public void setServers(List<Url> servers) {
        this.servers = servers;
    }

    public Map<String, Method> getPaths() {
        return paths;
    }

    private void setPaths(Map<String, Method> paths) {
        this.paths = paths;
    }

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    static public class Info {
        String version;
        String title;
        Licence license;

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Licence getlicense() {
            return this.license;
        }

        public void setlicense(Licence license) {
            this.license = license;
        }

    }

    static public class Licence {
        String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    static public class Servers {
        private Url[] arr;

        public Url[] getArr() {
            return arr;
        }

        public void setArr(Url[] arr) {
            this.arr = arr;
        }

    }

    static public class Url {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    static public class Method {

        private MethodParam get;
        private MethodParam post;

        public String toString(int iMargin) {
            String margin = "                                        ".substring(0, iMargin);
            String pom = new String();

            if (get != null) {
                pom += margin + "Get:\n";
                pom += margin + get.toString(iMargin + 2);
            }
            if (post != null) {
                pom += margin + "Post:\n";
                pom += margin + post.toString(iMargin + 2);
            }

            return pom;
        }

        public MethodParam getGet() {
            return get;
        }

        public void setGet(MethodParam get) {
            this.get = get;
        }

        public MethodParam getPost() {
            return post;
        }

        public void setPost(MethodParam post) {
            this.post = post;
        }

    }

    static public class MethodParam {

        private String summary;
        private String operationId;
        private List<String> tags;
        private List<Parameters> parameters;
        private Map<String, Responses> responses;

        public String toString(int iMargin) {
            String margin = "                                        ".substring(0, iMargin);
            String pom = new String();

            pom += ((summary == null) ? "" : margin + "summary:" + summary + "\n");
            pom += ((operationId == null) ? "" : margin + "operationId:" + operationId + "\n");

            if (tags != null) {
                pom += margin + "Tags:\n";
                for (String tag : tags) {
                    pom += margin + "  -" + tag + "\n";
                }
            }

            if (parameters != null) {
                pom += margin + "Parameters:\n";
                for (Parameters par : parameters) {
                    pom += par.toString(iMargin + 2);
                }
            }

            if (parameters != null) {
                pom += margin + "Responses:\n";
                for (Map.Entry resentry : responses.entrySet()) {
                    pom += margin + "  '" + resentry.getKey() + "':\n";
                    Responses resp = (Responses) resentry.getValue();
                    pom += resp.toString(iMargin + 4);
                }
            }

            return pom;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getOperationId() {
            return operationId;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<Parameters> getParameters() {
            return parameters;
        }

        public void setParameters(List<Parameters> parameters) {
            this.parameters = parameters;
        }

        public Map<String, Responses> getResponses() {
            return responses;
        }

        public void setResponses(Map<String, Responses> responses) {
            this.responses = responses;
        }

    }

    static public class Parameters {
        private String name;
        private String in;
        private String description;
        private Boolean required;
        private SchemaObject schema;

        public String toString(int iMargin) {
            String margin = "                                        ".substring(0, iMargin);
            String pom = new String();

            pom += ((name == null) ? "" : margin + "name:" + name + "\n");
            pom += ((in == null) ? "" : margin + "in:" + in + "\n");
            pom += ((description == null) ? "" : margin + "description:" + description + "\n");
            pom += ((required == null) ? "" : margin + "required:" + required.toString() + "\n");
            if (schema != null) {
                pom += margin + "  schema:\n";
                pom += schema.toString(iMargin + 2);

            }
            return pom;
        }

        public Parameters(String name, Boolean required) {
            this.name = name;
            this.required = required;
        }

        public Parameters() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIn() {
            return in;
        }

        public void setIn(String in) {
            this.in = in;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public SchemaObject getSchema() {
            return schema;
        }

        public void setSchema(SchemaObject schema) {
            this.schema = schema;
        }
    }

    static public class Components {
        private Map<String, Object> schemas;

        public Map<String, Object> getSchemas() {
            return schemas;
        }

        public void setSchemas(Map<String, Object> schemas) {
            this.schemas = schemas;
        }

    }

    static public class Responses {
        private String description;
        private Map<String, MediaTypeObject> content;
        private Map<String, HeaderObject> headers;

        public String toString(int iMargin) {
            String margin = "                                        ".substring(0, iMargin);
            String pom = new String();

            pom += ((description == null) ? "" : margin + "description:" + description + "\n");

            if (headers != null) {
                pom += margin + "headers:\n";
                for (Map.Entry head : headers.entrySet()) {
                    pom += margin + "  " + head.getKey() + ":\n";
                    HeaderObject headObj = (HeaderObject) head.getValue();
                    if (headObj != null) {
                        pom += headObj.toString(iMargin + 4);
                    }

                }

            }

            if (content != null) {
                pom += margin + "content:\n";
                for (Map.Entry media : content.entrySet()) {
                    pom += margin + "  " + media.getKey() + ":\n";
                    if ((MediaTypeObject) media.getValue() != null) {
                        SchemaObject schema = ((MediaTypeObject) media.getValue()).getSchema();
                        if (schema != null) {
                            pom += margin + "    schema:\n";
                            pom += schema.toString(iMargin + 6);
                        }
                    }

                }

            }

            return pom;

        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, MediaTypeObject> getContent() {
            return content;
        }

        public void setContent(Map<String, MediaTypeObject> content) {
            this.content = content;
        }

        public Map<String, HeaderObject> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, HeaderObject> headers) {
            this.headers = headers;
        }

    }

    static public class MediaTypeObject {
        private SchemaObject schema;

        public SchemaObject getSchema() {
            return schema;
        }

        public void setSchema(SchemaObject schema) {
            this.schema = schema;
        }
    }

    static public class SchemaObject {
        private String type;
        private String format;
        private Boolean readOnly;
        private Boolean writeOnly;
        @JsonProperty("$ref")
        private String SSref;
        private List<String> required;

        public String toString(int iMargin) {
            String margin = "                                        ".substring(0, iMargin);
            String pom = new String();

            pom += ((type == null) ? "" : margin + "type:" + type + "\n");
            pom += ((format == null) ? "" : margin + "format:" + format + "\n");
            pom += ((readOnly == null) ? "" : margin + "readOnly:" + readOnly.toString() + "\n");
            pom += ((writeOnly == null) ? "" : margin + "writeOnly:" + writeOnly.toString() + "\n");
            pom += ((SSref == null) ? "" : margin + "$ref:" + SSref + "\n");
            if (required != null) {
                pom += margin + "required:\n";
                for (String req : required) {
                    pom += margin + " - " + req + "\n";
                }
            }

            return pom;
        }

        public void RefDeserialize() {
            if (SSref != null) {
                if (SSref.substring(0, 1).equals("#")) {
                    // reference on the same file
                    int ist = SSref.indexOf("/", 2);

                }
            }
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public Boolean getReadOnly() {
            return readOnly;
        }

        public void setReadOnly(Boolean readOnly) {
            this.readOnly = readOnly;
        }

        public Boolean getWriteOnly() {
            return writeOnly;
        }

        public void setWriteOnly(Boolean writeOnly) {
            this.writeOnly = writeOnly;
        }

        public String getSSref() {
            return SSref;
        }

        public void setSSref(String sSref) {
            this.SSref = sSref;
        }

        public List<String> getRequired() {
            return required;
        }

        public void setRequired(List<String> required) {
            this.required = required;
        }

    }

    static public class HeaderObject {
        private String name;
        private String description;
        private SchemaObject schema;

        public String toString(int iMargin) {
            String margin = "                                        ".substring(0, iMargin);
            String pom = new String();

            pom += ((name == null) ? "" : margin + "name:" + name + "\n");
            pom += ((description == null) ? "" : margin + "description:" + description + "\n");
            if (schema != null) {
                pom += margin + "  schema:\n";
                pom += schema.toString(iMargin + 2);

            }
            return pom;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public SchemaObject getSchema() {
            return schema;
        }

        public void setSchema(SchemaObject schema) {
            this.schema = schema;
        }
    }

    static public class ExternalDocumentationObject {
        private String description;
        private String url;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}