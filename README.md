# Gulist

Presents a simple list (flat, ordered) of Guardian and other?
content. Lists are personalised per user against a variety of
expressed preferences.

Initially personalising against:

* journalists/authors
* topics (tags and sections)

And also leveraging:

* user votes
* freshness

But note, would be cool if it surfaced older stuff too.

## Initial how it works

1. Grab list from CAPI (last month/eventually use notifications to update)
2. Apply weightings and filters
3. Return

We should really subscribe to the CAPI notification service, and store
content in our API. This would also allow us to hold non-Guardian
content, which is consistent with the aim of presenting an aggregated
view.

## License

Copyright © 2015 The Guardian

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
