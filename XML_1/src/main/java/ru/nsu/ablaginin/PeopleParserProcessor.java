package ru.nsu.ablaginin;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PeopleParserProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final PeopleInfoNormalizer normalizer = new PeopleInfoNormalizer();

    private final XMLStreamReader reader;

    public PeopleParserProcessor(InputStream inputStream) throws XMLStreamException {
        this.reader = FACTORY.createXMLStreamReader(inputStream);
    }

    public List<PersonInfo> parse() throws XMLStreamException {
        int peopleCount = -1;

        List<PersonInfo> people = new ArrayList<>();

        PersonInfo personInfo = new PersonInfo();

        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLStreamConstants.START_ELEMENT) {
                switch (reader.getLocalName()) {
                    case "people" -> peopleCount = parsePeople();
                    case "person" -> parsePerson(personInfo);
                    case "id" -> parseField(personInfo::setId);
                    case "gender" -> parseField(personInfo::setGender);
                    case "spouce" -> parseField(personInfo::setSpouse);
                    case "husband" -> parseField(personInfo::setHusband);
                    case "wife" -> parseField(personInfo::setWife);
                    case "daughter" -> parseField(personInfo::addDaughterId);
                    case "son" -> parseField(personInfo::addSonId);
                    case "child" -> parseField(personInfo::addChild);
                    case "parent" -> parseField(personInfo::addParent);
                    case "father" -> parseField(personInfo::setFather);
                    case "mother" -> parseField(personInfo::setMother);
                    case "siblings" -> parseField(personInfo::addSiblingIds);
                    case "surname", "family", "family-name" ->
                            parseField(personInfo::setFamilyName);
                    case "first", "firstname" ->
                            parseField(personInfo::setFirstName);
                    case "fullname", "children" -> {
                    }
                    case "sister" -> parseField(personInfo::addSisterName);
                    case "brother" -> parseField(personInfo::addBrotherName);
                    case "siblings-number" ->
                            parseField(personInfo::setSiblingsNumber);
                    case "children-number" ->
                            parseField(personInfo::setChildrenNumber);

                    default ->
                            printUnknownAttribute("parse whole xml", reader.getLocalName());
                }
            } else if (event == XMLStreamConstants.END_ELEMENT && reader.getLocalName().equals("person")) {
                people.add(personInfo);
                personInfo = new PersonInfo();
            }
        }
        return normalizer.normalize(people, peopleCount);
    }

    private int parsePeople() {
        int peopleCount = Integer.parseInt(reader.getAttributeValue(0));
        System.out.println("Total people: " + peopleCount);
        return peopleCount;
    }

    private void parsePerson(PersonInfo personInfo) {
        if (reader.getAttributeCount() > 0) {
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                switch (reader.getAttributeLocalName(i)) {
                    case "name" ->
                            personInfo.setFullName(reader.getAttributeValue(i));
                    case "id" -> personInfo.setId(reader.getAttributeValue(i));
                    default ->
                            printUnknownAttribute("parse person", reader.getAttributeValue(i));
                }
            }
        }
    }

    private void parseField(Consumer<String> consumer) throws XMLStreamException {
        if (reader.getAttributeCount() > 0) {
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                switch (reader.getAttributeLocalName(i)) {
                    case "id", "val", "value" -> {
                        String attributeValue = reader.getAttributeValue(i);
                        if (!attributeValue.equals("NONE") &&
                                !attributeValue.equals("UNKNOWN") &&
                                !attributeValue.isBlank()) {
                            consumer.accept(reader.getAttributeValue(i));
                        }
                    }
                    default ->
                            printUnknownAttribute("parse field", reader.getAttributeValue(i));
                }
            }
        } else if (reader.hasText()) {
            reader.next();
            String text = reader.getText();
            if (validateFieldValue(text)) consumer.accept(text);
        } else {
            reader.next();
            if (reader.hasText()) {
                String text = reader.getText();
                if (validateFieldValue(text)) consumer.accept(text);
            }
        }
    }

    private boolean validateFieldValue(String text) {
        return !text.trim().equals("NONE") &&
                !text.trim().equals("UNKNOWN") &&
                !text.trim().isBlank();
    }

    private void printUnknownAttribute(String action, String value) {
        System.out.println("UNKNOWN ATTRIBUTE while " + action + " " + value);
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                System.out.println("Failed to parse XML");
            }
        }
    }
}
