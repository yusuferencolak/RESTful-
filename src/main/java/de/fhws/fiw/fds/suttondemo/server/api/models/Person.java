/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.suttondemo.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@JsonRootName("person")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "person")
public class Person extends AbstractModel {

    private String firstName;
    private String lastName;

    private LocalDate birthDate;
    private String emailAddress;

    @SuttonLink(
            value = "persons/${id}",
            rel = "self"
    )
    private transient Link selfLink;

    @SuttonLink(
            value = "persons/${id}/locations",
            rel = "getLocationsOfPerson"
    )
    private transient Link location;

    public Person() {
        // make JPA happy
    }

    public Person(final String firstname, final String lastname, final String emailAddress,
                  final LocalDate birthdate) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.birthDate = birthdate;
        this.emailAddress = emailAddress;
    }

    public Link getSelfLink() {
        return selfLink;
    }
    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    public Link getLocation() {
        return location;
    }

    public void setLocation(Link location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", birthDate=" + birthDate + '\''
                + ", emailAddress='" + emailAddress + '\''
                + '}';
    }
}
