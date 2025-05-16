FILESEXTRAPATHS:prepend:rcar := "${THISDIR}/${PN}:"

SRC_URI:append:rcar-gen3 = " \
    file://wired.network \
"
