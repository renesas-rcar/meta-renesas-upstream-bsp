# OpenEmbedded/Yocto BSP layer for Renesas Electronics's SoCs

This layer provides support for Renesas Electronics's platforms for use with OpenEmbedded and/or Yocto.

Layer maintainer(s):

* Please see the MAINTAINERS file for more details.


## Supported Boards/Machines

In addition, this also provides layer which supports boards using Renesas SoCs.
Layers and boards that support is the following:

    - Renesas Electronics Corporation. R-Car Salvator-X (H3: R8A77950/R8A77951, M3: R8A77960/R8A77961, M3N: R8A77965)
    - Renesas Electronics Corporation. R-Car Starter Kit premier(H3ULCB) (R8A77951)
    - Renesas Electronics Corporation. R-Car Starter Kit pro(M3ULCB) (R8A77960/R8A77961)
    - Renesas Electronics Corporation. R-Car Starter Kit pro(M3NULCB) (R8A77965)
    - Renesas Electronics Corporation. R-Car Ebisu (R8A77990)
    - Renesas Electronics Corporation. R-Car Draak (R8A77995)
    - Renesas Electronics Corporation. R-Car S4 Starter Kit (R8A779F0)
    - Renesas Electronics Corporation. R-Car Spider (R8A779F0)



## Original BSP image

* core-image-minimal


## Contribution

* Please submit any patches for this layer to: rcar-yocto@lm.renesas.com

* Please see the MAINTAINERS file for more details.


## Layer Dependencies

This layer depends on:

* poky

```bash
    URI: git://git.yoctoproject.org/poky
    layers: meta, meta-poky, meta-yocto-bsp
    branch: scarthgap
```

* meta-openembedded

```bash
    URI: git://git.openembedded.org/meta-openembedded
    layers: meta-oe, meta-python, meta-networking
    branch: scarthgap
```


# Build Instructions

## Required Environment

* Refer to https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html to prepare Build Host.

* This also needs git user name and email defined:

```bash
   $ git config --global user.email "you@example.com"
   $ git config --global user.name "Your Name"
```

* Then, using one of three way to build Image. (Manual Build, Using template configuration, Using Renesas'script)

## Manual Build

* Create a directory as working folder

```bash
    $ mkdir build
    $ cd build
    $ export WORK=`pwd`
```

* Clone required layers

```bash
    $ cd $WORK
    $ git clone git://git.yoctoproject.org/poky
    $ git clone git://git.openembedded.org/meta-openembedded
    $ git clone https://github.com/renesas-rcar/meta-renesas-upstream-bsp.git
```

* Switch to proper branches/commits

```bash
    $ cd $WORK
    $ git -C poky checkout -b scarthgap origin/scarthgap
    $ git -C meta-openembedded checkout -b scarthgap origin/scarthgap
    $ git -C meta-renesas-upstream-bsp checkout -b scarthgap origin/scarthgap
```

* Initialize a build using the 'oe-init-build-env' script in Poky. e.g.:

```bash
    $ cd $WORK
    $ source poky/oe-init-build-env
```

* After that, initialized configure bblayers.conf by adding meta-rcar-gen3 layer.
e.g.:

```bash
    BBLAYERS ?= " \
        <path to layer>/poky/meta \
        <path to layer>/poky/meta-poky \
        <path to layer>/poky/meta-yocto-bsp \
        <path to layer>/meta-renesas-upstream-bsp \
        <path to layer>/meta-openembedded/meta-oe \
        <path to layer>/meta-openembedded/meta-python \
        <path to layer>/meta-openembedded/meta-networking \
    "
```

* To build a specific target BSP, configure the associated machine in local.conf:

```bash
    MACHINE ??= "<supported board name>"
```

Board|MACHINE
-----|-------
Salvator-X/XS|MACHINE = "salvator-x"
Ebisu|MACHINE = "ebisu"
Starter Kit Pro (M3ULCB)|MACHINE = "m3ulcb"
Starter Kit Pro (M3NULCB)|MACHINE = "m3nulcb"
Starter Kit Premier (H3ULCB)|MACHINE = "h3ulcb"
Draak|MACHINE = "draak"
S4 Starter Kit|MACHINE = "s4sk"
S4 Spider|MACHINE = "spider"

* Select the SOC

    * For H3: r8a7795

    ```bash
        SOC_FAMILY = "r8a7795"
    ```

    * For M3: r8a7796

    ```bash
        SOC_FAMILY = "r8a7796"
    ```

    * For M3N: r8a77965

    ```bash
        SOC_FAMILY = "r8a77965"
    ```

    * For E3: r8a77990

    ```bash
        # Already added in machine config: ebisu.conf
        SOC_FAMILY = "r8a77990"
    ```

    * For D3: r8a77995

    ```bash
        # Already added in machine config: draak.conf
        SOC_FAMILY = "r8a77995"
    ```

    * For s4 Starter Kit: r8a779f0

    ```bash
        # Already added in machine config: s4sk.conf
        SOC_FAMILY = "r8a779f0"
    ```

    * For Spider: r8a779f0

    ```bash
        # Already added in machine config: spider.conf
        SOC_FAMILY = "r8a779f0"
    ```

* Configure for systemd init in local.conf:

```bash
    DISTRO_FEATURES:append = " usrmerge systemd"
    VIRTUAL-RUNTIME_init_manager = "systemd"
```

* Configure for ivi-shell and ivi-extension

```bash
    DISTRO_FEATURES:append = " ivi-shell"
```

* Configure for USB 3.0

```bash
    MACHINE_FEATURES:append = " usb3"
```

* Enable tuning support for Capacity Aware migration Strategy (CAS)

```bash
    MACHINE_FEATURES:append = " cas"
```

* For a list of sample local.conf file, please refer to: [conf/templates/](conf/templates/)

* Build the target file system image using bitbake:

```bash
    $ bitbake core-image-minimal
```

After completing the images for the target machine will be available in the
output directory 'tmp/deploy/images/<supported board name>'.

Images generated:

* Image (generic Linux Kernel binary image file)

* \<SoC\>-\<machine name\>.dtb (DTB for target machine)

* core-image-minimal-\<machine name\>.tar.bz2 (rootfs tar+bzip2)

* core-image-minimal-\<machine name\>.ext4  (rootfs ext4 format)


## Using template configuration

* Create a directory as working folder

```bash
    $ mkdir build
    $ cd build
    $ export WORK=`pwd`
```

* Clone required layers

```bash
    $ cd $WORK
    $ git clone git://git.yoctoproject.org/poky
    $ git clone git://git.openembedded.org/meta-openembedded
    $ git clone https://github.com/renesas-rcar/meta-renesas-upstream-bsp.git
```

* Switch to proper branches/commits

```bash
    $ cd $WORK
    $ git -C poky checkout -b scarthgap origin/scarthgap
    $ git -C meta-openembedded checkout -b scarthgap origin/scarthgap
    $ git -C meta-renesas-upstream-bsp checkout -b scarthgap origin/scarthgap
```

* Initialize a build using the 'oe-init-build-env' script in Poky. e.g.:

```bash
    $ cd $WORK
    $ source poky/oe-init-build-env
```

* Copy sample configuration to build folder. e.g.:

```bash
    $ cp ../meta-renesas-upstream-bsp/conf/templates/<board/machine>/local.conf.sample conf/local.conf
    $ cp ../meta-renesas-upstream-bsp/conf/templates/<board/machine>/bblayers.conf.sample conf/bblayers.conf
```

* <board/machine>: h3ulcb, m3ulcb, m3nulcb, salvator-x, ebisu, draak, s4sk, spider

* With Salvator-x, edit SOC_FAMILY into "conf/local.conf"

    * For H3: r8a7795

    ```bash
        SOC_FAMILY = "r8a7795"
    ```

    * For M3: r8a7796

    ```bash
        SOC_FAMILY = "r8a7796"
    ```

    * For M3N: r8a77965

    ```bash
        SOC_FAMILY = "r8a77965"
    ```

Example:

```bash
    $ cp ../meta-renesas-upstream-bsp/conf/templates/h3ulcb/local.conf.sample conf/local.conf
    $ cp ../meta-renesas-upstream-bsp/conf/templates/h3ulcb/bblayers.conf.sample conf/bblayers.conf
```

* Build the target file system image using bitbake:

```bash
    $ bitbake core-image-minimal
```


## Using Renesas'script

* Create a directory as working folder

```bash
    $ mkdir build
    $ cd build
    $ export WORK=`pwd`
```

* Clone required layers

```bash
    $ cd $WORK
    $ git clone git://git.yoctoproject.org/poky
    $ git clone git://git.openembedded.org/meta-openembedded
    $ git clone https://github.com/renesas-rcar/meta-renesas-upstream-bsp.git
```

* Switch to proper branches/commits

```bash
    $ cd $WORK
    $ git -C poky checkout -b scarthgap origin/scarthgap
    $ git -C meta-openembedded checkout -b scarthgap origin/scarthgap
    $ git -C meta-renesas-upstream-bsp checkout -b scarthgap origin/scarthgap
```

* Run Renesas'script to build

```bash
    $ cd $WORK
    $ source meta-renesas-upstream-bsp/init-env-and-build <board/machine> <kernel_version> <builddir>
```

* <board/machine>: h3ulcb, m3ulcb, m3nulcb, h3, m3, m3n, ebisu, draak, s4sk, spider
* <kernel_version>: 6.1, 6.6

Example:

```bash
    $ source meta-renesas-upstream-bsp/init-env-and-build m3n 6.1 build_m3n_kernel_6.1
```


## Build Instructions for SDK

NOTE:

**This may be changed in the near feature. These instructions are tentative.**

Should define the staticdev in SDK image feature for installing the static libs
to SDK in local.conf.

```bash
    SDKIMAGE_FEATURES:append = " staticdev-pkgs"
```


### For 64-bit target SDK (aarch64)

Use `bitbake -c populate_sdk` for generating the toolchain SDK

```bash
    $ bitbake core-image-minimal -c populate_sdk
```

The SDK can be found in the output directory `tmp/deploy/sdk`

* `poky-glibc-x86_64-core-image-minimal-aarch64-<machine name>-toolchain-x.x.sh`


### Usage of toolchain SDK

Install the SDK to the default: `/opt/poky/x.x`

* For 64-bit target SDK

```bash
    $ sh poky-glibc-x86_64-core-image-minimal-aarch64-<machine name>-toolchain-x.x.sh
```

* For 64-bit application, using environment script in `/opt/poky/x.x`

```bash
    $ source /opt/poky/x.x/environment-setup-aarch64-poky-linux
```


## R-Car Information

Refer to the following for more information from eLinux website

https://elinux.org/R-Car
