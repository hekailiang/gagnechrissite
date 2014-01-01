package com.gagnechris.backend.model

import spray.json.{JsonFormat, DefaultJsonProtocol}

case class LinkedInSkills(_total: Int, values: Option[List[LinkedInSkill]])
case class LinkedInSkill(id: Option[Long], skill: Option[LinkedInSkillDetail])
case class LinkedInSkillDetail(name: Option[String])
case class LinkedInStandardRequest(url: Option[String])
case class LinkedInCompany(id: Option[Long], industry: Option[String], name: Option[String], size: Option[String], `type`: Option[String])
case class LinkedInPosition(company: Option[LinkedInCompany], id: Option[Long], isCurrent: Option[Boolean], startDate: Option[LinkedInDate],
  summary: Option[String], title: Option[String])
case class LinkedInPositions(_total: Int, values: Option[List[LinkedInPosition]])
case class LinkedInLocation(country: Option[LinkedInCountry], name: Option[String])
case class LinkedInCountry(code: Option[String])
case class LinkedInToken(expires_in: Int, access_token: String)
case class LinkedInEducations(_total: Int, values: Option[List[LinkedInEducation]])
case class LinkedInEducation(degree: Option[String], endDate: Option[LinkedInDate], fieldOfStudy: Option[String],
  id: Option[Long], schoolName: Option[String], startDate: Option[LinkedInDate])
case class LinkedInDate(year: Option[Int], month: Option[Int])
case class LinkedInProfile(educations: Option[LinkedInEducations], firstName: Option[String], headline: Option[String], id: Option[String], industry: Option[String],
  lastName: Option[String], location: Option[LinkedInLocation], numConnections: Option[Int], pictureUrl: Option[String],
  positions: Option[LinkedInPositions], siteStandardProfileRequest: Option[LinkedInStandardRequest], skills: Option[LinkedInSkills],
  summary: Option[String])

object LinkedInJsonProtocol extends DefaultJsonProtocol {
  implicit def LinkedInDateFormat = jsonFormat2(LinkedInDate)
  implicit def LinkedInCompanyFormat = jsonFormat5(LinkedInCompany)
  implicit def LinkedInPositionFormat = jsonFormat6(LinkedInPosition)
  implicit def LinkedInPoistionsFormat = jsonFormat2(LinkedInPositions)
  implicit def LinkedInCountryFormat = jsonFormat1(LinkedInCountry)
  implicit def LinkedInLocationFormat = jsonFormat2(LinkedInLocation)
  implicit def LinkedInEducationFormat = jsonFormat6(LinkedInEducation)
  implicit def LinkedInEducationsFormat = jsonFormat2(LinkedInEducations)
  implicit def LinkedInStandardRequestFormat = jsonFormat1(LinkedInStandardRequest)
  implicit def LinkedInSkillDetailFormat = jsonFormat1(LinkedInSkillDetail)
  implicit def LinkedInSkillFormat = jsonFormat2(LinkedInSkill)
  implicit def LinkedInSkillsFormat = jsonFormat2(LinkedInSkills)

  implicit def LinkedInProfileFormat = jsonFormat13(LinkedInProfile)
  implicit def LinkedInTokenFormat = jsonFormat2(LinkedInToken)
}
